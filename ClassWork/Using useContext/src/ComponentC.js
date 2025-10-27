import {useContext} from "react";
import { UserContext } from "./ComponentA";
import ComponentD from "./ComponentD";
const ComponentC = () =>{
    const user = useContext(UserContext);

    return(
    <div className="box">
        <p>ComponentC</p>
        <p>{`Hello again ${user}`}</p>
        <ComponentD />
    </div>);
}
export default ComponentC
