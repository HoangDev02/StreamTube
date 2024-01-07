import { initializeApp } from "firebase/app";
import {getAuth,GoogleAuthProvider} from "firebase/auth";
const firebaseConfig = {
    apiKey: "AIzaSyDlh5fiba3dMsOU6sEEIc6Wxt5JTNQp5YY",
    authDomain: "garden-app-v1.firebaseapp.com",
    databaseURL: "https://garden-app-v1-default-rtdb.firebaseio.com",
    projectId: "garden-app-v1",
    storageBucket: "garden-app-v1.appspot.com",
    messagingSenderId: "194631066135",
    appId: "1:194631066135:web:e9cfd1f208a0c7711cb252",
    measurementId: "G-D2E1KPHJJR"
  };

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth(app)
const provider = new GoogleAuthProvider();
export {auth,provider};