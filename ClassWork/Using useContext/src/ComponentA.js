import react from "react";
import {useState, createContext} from "react";
import ComponentB from "./ComponentB";

export const UserContext = createContext();
const ComponentA = () =>{


    const [user, setUser] = useState("Yogesh");

    return(
    <div className="box">
        <p>ComponentA</p>
        <p>{`Hello ${user}`}</p>
        <UserContext.Provider value={user}>
            <ComponentB user={user}/>

        </UserContext.Provider>
        
    </div>);
}
export default ComponentA
