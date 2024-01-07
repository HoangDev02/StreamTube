import React, { useEffect, useState } from "react";
import { auth, provider } from "./config";
import { signInWithPopup } from "firebase/auth";
import Home from "../home/Home";

function SignEmail() {
  const [user, setUser] = useState(null);

  const handleClick = () => {
    signInWithPopup(auth, provider).then((result) => {
      setUser({
        email: result.user.email,
      });

      localStorage.setItem(
        "user",
        JSON.stringify({
          email: result.user.email,
        })
      );
    });
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
        <button onClick={handleClick} className="text-lg text-red-500">Login Email</button>
      )}
    </div>
  );
}
export default SignEmail;
