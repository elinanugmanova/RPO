import React from 'react';
import { Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faGlobe,
    faPalette,
    faLandmark,
    faImage,
    faUsers
} from '@fortawesome/free-solid-svg-icons';

const SideBar = props => {
    return (
        <>
        { props.expanded &&
            <Nav className={"flex-column my-sidebar my-sidebar-expanded"}>
                <Nav.Item>
                    <Nav.Link as={Link} to="/countries">
                        <FontAwesomeIcon icon={faGlobe} />{' '}Страны
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/artists">
                        <FontAwesomeIcon icon={faPalette} />{' '}Художники
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/museums">
                        <FontAwesomeIcon icon={faLandmark} />{' '}Музеи
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/paintings">
                        <FontAwesomeIcon icon={faImage} />{' '}Картины
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/users">
                        <FontAwesomeIcon icon={faUsers} />{' '}Пользователи
                    </Nav.Link>
                </Nav.Item>
            </Nav>
        }
        { !props.expanded &&
            <Nav className={"flex-column my-sidebar my-sidebar-collapsed"}>
                <Nav.Item>
                    <Nav.Link as={Link} to="/countries" title="Страны">
                        <FontAwesomeIcon icon={faGlobe} size="lg" />
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/artists" title="Художники">
                        <FontAwesomeIcon icon={faPalette} size="lg" />
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/museums" title="Музеи">
                        <FontAwesomeIcon icon={faLandmark} size="lg" />
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/paintings" title="Картины">
                        <FontAwesomeIcon icon={faImage} size="lg" />
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/users" title="Пользователи">
                        <FontAwesomeIcon icon={faUsers} size="lg" />
                    </Nav.Link>
                </Nav.Item>
            </Nav>
        }
        </>
    )
}

export default SideBar;