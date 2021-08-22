import { useEffect, useState } from 'react';
import logo from './logo.svg';
import './App.css';
import { Table, Container, Button, Modal, DropdownButton, Dropdown } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  const [newTodoDescription, updateNewTodoDescription] = useState('');
  const [currentUser, setCurrentUser] = useState('default');
  
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  //  Show/hide todo edit 
  const [showEdit, setShowEdit] = useState(false);
  const handleCloseEdit = () => setShowEdit(false);
  const [todoEditId, updateTodoEditId] = useState(1);
  const [stateFeatureFlags, updateStateFeatureFlags] = useState({createEnabled: false, editEnabled: false, deleteEnabled: false})

  const url = process.env.NODE_ENV === "development" ? "http://localhost:8080" : "https://demo-1626050777742.azurewebsites.net";

  const [todoList, updateTodoList] = useState([{id: 1, item: 'Launch Java App'}, {id: 2, item: 'Launch React App'}, 
    {id: 3, item: 'Create Frontend App'}, {id: 4, item: 'Create REST API Storage Calls'}, 
    {id: 5, item: 'Implement Togglz Feature Flags on TODO App'}])

  useEffect(() => {
    readOnClickHandler();
    fetch(`${url}/api/features/states?user=${currentUser}`, {
      method: "GET",
      headers: { "Content-Type": "application/json" }})
        .then(response => response.json())
        .then(formattedResponse => {
          updateStateFeatureFlags(formattedResponse);
        })
  }, [currentUser])

  const createOnClickHandler = async (e) => {
    await fetch(`${url}/api/create/`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ description: newTodoDescription })
    })
    await readOnClickHandler();
  }

  const readOnClickHandler = async (e) => {
    const response = await fetch(`${url}/api/read/`, {
      method: "GET",
      headers: { "Content-Type": "application/json" }
    });
    const body = await response.json();
    console.log(body);
    updateTodoList(body || [])

  }

  const editShowModalHandler = (state) => setShowEdit(state)

  const editOnClickHandler = async (e) => {
    console.log(todoEditId)
    await fetch(`${url}/api/edit?user=${currentUser}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id: todoEditId, description: newTodoDescription, user: currentUser })
    })
    await readOnClickHandler();
  }

  const deleteOnClickHandler = async (e) => {
    await fetch(`${url}/api/delete?user=${currentUser}`, {
      method: "DELETE",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id: e?.target?.value, description: newTodoDescription, user: currentUser })
    })
    await readOnClickHandler();
  }
  const onCreateTodoDescriptionChange = (e) => updateNewTodoDescription(e?.target?.value)



  return (
    <div className="App">
      <Container style={{
        height: "100vh", display: "flex", alignItems: "center", justifyContent: "center",
        flexDirection: "column"
      }}>
        <h1>TODO App: Logged In User </h1>
        <DropdownButton id="dropdown-basic-button" title={`Current User: ${currentUser}`}>
          <Dropdown.Item href="#/action-1" onClick={() => setCurrentUser("default")}>default</Dropdown.Item>
          <Dropdown.Item href="#/action-2" onClick={() => setCurrentUser("evan")}>admin evan</Dropdown.Item>
          <Dropdown.Item href="#/action-3" onClick={() => setCurrentUser("bob")}>user bob</Dropdown.Item>
          <Dropdown.Item href="#/action-3" onClick={() => setCurrentUser("dan")}>user dan</Dropdown.Item>
        </DropdownButton>
        <Table striped bordered hover style={{ height: "40%", width: "50%" }}>
          <thead>
            <tr>
              <th>ID</th>
              <th>Description</th>
              <th>Edit</th>
              <th>Delete</th>
            </tr>
          </thead>
          <tbody>
            {todoList.map((todoItem) =>
              <tr key={todoItem?.id}>
                <td>{todoItem?.id}</td>
                <td>{todoItem?.item}</td>
                <td><Button name={todoItem} disabled={!stateFeatureFlags?.editEnabled} onClick={() => { updateTodoEditId(todoItem?.id); editShowModalHandler(true)}} >Edit</Button></td>
                <td><Button name={todoItem} disabled={!stateFeatureFlags?.deleteEnabled} onClick={deleteOnClickHandler} value={todoItem?.id}>Delete</Button></td>
              </tr>)}
          </tbody>
        </Table>
        <div style={{ display: "flex", justifyContent: "space-between", width: "50%" }} >
          <Button variant="primary" onClick={readOnClickHandler}>Read Data</Button>
          <Button variant="primary" onClick={handleShow} disabled={!stateFeatureFlags?.createEnabled}>
            Create Todo
          </Button>
        </div>

        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>New Todo</Modal.Title>
          </Modal.Header>
          <Modal.Body>Enter Description: <input type="text" onChange={onCreateTodoDescriptionChange}></input></Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              Close
            </Button>
            <Button variant="primary" onClick={createOnClickHandler}>
              Save Changes
            </Button>
          </Modal.Footer>
        </Modal>

        <Modal show={showEdit} onHide={handleCloseEdit}>
          <Modal.Header closeButton>
            <Modal.Title>Edit Todo</Modal.Title>
          </Modal.Header>
          <Modal.Body>Enter New Description: <input type="text" onChange={onCreateTodoDescriptionChange}></input></Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseEdit}>
              Close
            </Button>
            <Button variant="primary" onClick={editOnClickHandler}>
              Save Changes
            </Button>
          </Modal.Footer>
        </Modal>
      </Container>
    </div>
  );
}

export default App;
