import React from "react";
import { useRef, useState, useEffect, useContext } from "react";
import { Form } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import AuthContext from "./context/AuthProvider";
import axios from "./api/axios";
import './styles/Login.css'
import { Link } from "react-router-dom";



const Login = () => {
  const LOGIN_URL = "/authenticate";
  const { setAuth } = useContext(AuthContext);
  const userRef = useRef();
  const errRef = useRef();

  const [user, setUser] = useState("");
  const [pwd, setPwd] = useState("");
  const [errMsg, setErrMsg] = useState("");
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    userRef.current.focus();
  }, []);

  useEffect(() => {
    setErrMsg("");
  }, [user, pwd]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        LOGIN_URL,
        JSON.stringify({ userName: user, userPassword: pwd }),
        {
          headers: { "Content-Type": "application/json" },

          withCredentials: false,
        }
      );
      const accessToken = response?.data?.accessToken;
      const roles = response?.data?.roles;
      setAuth({user, pwd, accessToken});
      setUser('');
      setPwd('');
      setSuccess(true);
    } catch (err) {
        if (!err?.response) {
            setErrMsg('Ryšio problemos');
        } else if (err.response?.status === 400) {
            setErrMsg('Trūksta vartotojo vardo ar slaptažodžio');
        } else if (err.response?.status === 401) {
            setErrMsg('Neautorizuota');
        } else {
            setErrMsg('Prisujingimas nepavyko');
        }
        errRef.current.focus();
    }

    
  };

  return (
    <div>
      {success ? (
        <section>
          <h1>Prisijungėte sėkmingai!</h1>
          <br />
          <p>
            <a href="#">Titulinis</a>
          </p>
        </section>
      ) : (
        <section>
          <p
            ref={errRef}
            className={errMsg ? "errmsg" : "offscreen"}
            aria-live="assertive"
          >
            {errMsg}
          </p>
          <h1>Prisijungti</h1>

          <Form onSubmit={handleSubmit}>
            {/******************* USERNAME ******************************************/}
            <Form.Group className="mb-3">
              <Form.Label htmlFor="username">Vartotojas:</Form.Label>
              <Form.Control
                type="text"
                placeholder="Vartotojo vardas"
                id="username"
                ref={userRef}
                autoComplete="on"
                onChange={(e) => setUser(e.target.value)}
                value={user}
                required
              />
            </Form.Group>

            {/******************* PASSWORD ******************************************/}
            <Form.Group className="mb-3">
              <Form.Label htmlFor="password">Slaptažodis:</Form.Label>
              <Form.Control
                type="password"
                placeholder="Slaptažodis"
                id="password"
                ref={userRef}
                onChange={(e) => setPwd(e.target.value)}
                value={pwd}
                required
              />
            </Form.Group>
            <Button variant="warning" type="submit">
              Prisijungti
            </Button>
          </Form>
          <p>
            Registruotis?
            <br />
            <span className="line">
              <Link to="/registration">Registruotis</Link>
            </span>
          </p>
        </section>
      )}
      ;
    </div>
  );
};

export default Login;
