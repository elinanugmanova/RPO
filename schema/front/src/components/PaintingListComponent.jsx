import React, { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash, faEdit, faPlus } from '@fortawesome/free-solid-svg-icons';
import Alert from './Alert';
import BackendService from "../services/BackendService";
import { useNavigate } from 'react-router-dom';
import PaginationComponent from './PaginationComponent';

const PaintingListComponent = () => {
    const [message, setMessage] = useState();
    const [paintings, setPaintings] = useState([]);
    const [selectedPaintings, setSelectedPaintings] = useState([]);
    const [showAlert, setShowAlert] = useState(false);
    const [checkedItems, setCheckedItems] = useState([]);
    const [hidden, setHidden] = useState(false);
    const navigate = useNavigate();
    const [page, setPage] = useState(0);
    const [totalCount, setTotalCount] = useState(0);
    const limit = 2;

    const onPageChanged = cp => {
        refreshPaintings(cp - 1);
    };

    const setChecked = (value) => {
        setCheckedItems(Array(paintings.length).fill(value));
    };

    const handleCheckChange = (e) => {
        const idx = e.target.name;
        const isChecked = e.target.checked;
        const checkedCopy = [...checkedItems];
        checkedCopy[idx] = isChecked;
        setCheckedItems(checkedCopy);
    };

    const handleGroupCheckChange = (e) => {
        setChecked(e.target.checked);
    };

    const deletePaintingsClicked = () => {
        const selected = paintings.filter((_, idx) => checkedItems[idx]);
        if (selected.length > 0) {
            const msg = selected.length > 1
                ? `Подтвердите удаление ${selected.length} картин`
                : `Подтвердите удаление картины "${selected[0].name}"`;
            setShowAlert(true);
            setSelectedPaintings(selected);
            setMessage(msg);
        }
    };

    const refreshPaintings = (cp = 0) => {
        BackendService.retrieveAllPaintings(cp, limit)
            .then(resp => {
                setPaintings(resp.data.content);
                setHidden(false);
                setTotalCount(resp.data.totalElements);
                setPage(cp);
            })
            .catch(() => {
                setHidden(true);
                setTotalCount(0);
            })
            .finally(() => setChecked(false));
    };

    useEffect(() => {
        refreshPaintings();
    }, []);

    const updatePaintingClicked = (id) => {
        navigate(`/paintings/${id}`);
    };

    const onDelete = () => {
        BackendService.deletePaintings(selectedPaintings)
            .then(() => refreshPaintings())
            .catch(console.error);
    };

    const closeAlert = () => {
        setShowAlert(false);
    };

    const addPaintingClicked = () => {
        navigate('/paintings/new');
    };

    if (hidden) return null;

    return (
        <div className="m-4">
            <div className="row my-2">
                <h3>Картины</h3>
                <div className="btn-toolbar">
                    <div className="btn-group ms-auto">
                        <button className="btn btn-outline-secondary" onClick={addPaintingClicked}>
                            <FontAwesomeIcon icon={faPlus} /> Добавить
                        </button>
                    </div>
                    <div className="btn-group ms-2">
                        <button className="btn btn-outline-secondary" onClick={deletePaintingsClicked}>
                            <FontAwesomeIcon icon={faTrash} /> Удалить
                        </button>
                    </div>
                </div>
            </div>
            <div className="row my-2 me-0">
                <PaginationComponent
                    totalRecords={totalCount}
                    pageLimit={limit}
                    currentPage={page}
                    pageNeighbours={1}
                    onPageChanged={onPageChanged} />
                <table className="table table-sm">
                    <thead className="thead-light">
                        <tr>
                            <th>Название</th>
                            <th>Год создания</th>
                            <th>Художник</th>
                            <th>Музей</th>
                            <th>
                                <div className="btn-toolbar pb-1">
                                    <div className="btn-group ms-auto">
                                        <input type="checkbox" onChange={handleGroupCheckChange} />
                                    </div>
                                </div>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {paintings.map((painting, index) => (
                            <tr key={painting.id}>
                                <td>{painting.name}</td>
                                <td>{painting.year}</td>
                                <td>{painting.artist?.name}</td>
                                <td>{painting.museum?.name}</td>
                                <td>
                                    <div className="btn-toolbar">
                                        <div className="btn-group ms-auto">
                                            <button
                                                className="btn btn-outline-secondary btn-sm"
                                                onClick={() => updatePaintingClicked(painting.id)}
                                            >
                                                <FontAwesomeIcon icon={faEdit} fixedWidth />
                                            </button>
                                        </div>
                                        <div className="btn-group ms-2 mt-1">
                                            <input
                                                type="checkbox"
                                                name={index}
                                                checked={checkedItems[index] || false}
                                                onChange={handleCheckChange}
                                            />
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <Alert
                title="Удаление"
                message={message}
                ok={onDelete}
                close={closeAlert}
                modal={showAlert}
                cancelButton={true}
            />
        </div>
    );
};

export default PaintingListComponent;