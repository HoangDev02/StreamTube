import React, { useEffect, useState } from "react";
import { auth, provider } from "./config";
import { signInWithEmailAndPassword } from "firebase/auth";
import Home from "../home/Home";
import SignEmail from "./SignEmail";

function SignIn() {
  const [user, setUser] = useState(null);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null); // Add state for error handling

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const result = await signInWithEmailAndPassword(auth, email, password);
      setUser({ email: result.user.email });
      localStorage.setItem(
        "user",
        JSON.stringify({ email: result.user.email })
      );
    } catch (error) {
      setError(error.message);
    }
  };

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    setUser(user);
  }, []);

  return (
    <div>
      {user ? (
        <Home username={user.email} />
      ) : (
        <form onSubmit={handleSubmit}>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Email"
          />
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Password"
          />
          {error && <div className="error">{error}</div>}
          <button type="submit">Đăng nhập</button>
        </form>
      )}
      <SignEmail/>
    </div>
  );
}

export default SignIn;
