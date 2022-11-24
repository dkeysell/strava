import { navigationContext } from "./app";
import { useContext } from "react"; 
import navValues from "../helpers/navValues";

const SegmentRow = ({segment}) => {
    const { navigate} = useContext(navigationContext);
    return (
        <tr onClick={() => navigate(navValues.segment, segment)}>
            <td>{segment.name}</td>
            <td>{segment.qomWatts}</td>
        </tr>
    );
}

export default SegmentRow;