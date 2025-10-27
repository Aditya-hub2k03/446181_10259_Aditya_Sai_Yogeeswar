import react from "react";
import {useContext} from "react";
import { UserContext } from "./ComponentA";
const ComponentD = () =>{

    const user=useContext(UserContext);

    return(
    <div className="box">
        <p>ComponentD</p>
        <p>{`Bye ${user}`}</p>
    </div>);
}
export default ComponentD
