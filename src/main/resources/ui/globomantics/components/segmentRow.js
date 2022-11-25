import { navigationContext } from "./app";
import { useContext } from "react"; 
import navValues from "../helpers/navValues";

const SegmentRow = ({segment}) => {
    const { navigate} = useContext(navigationContext);
    return (
        <tr onClick={() => navigate(navValues.segment, segment)}>
            <td>{segment.name}</td>
            <td>{segment.qomWatts}</td>
	<td>{segment.komWatts}</td>
	<td>{segment.distance}</td>
	<td>{segment.average_grade}</td>
	<td>{segment.maximum_grade}</td>
	<td>{segment.elevation_low}</td>
	<td>{segment.elevation_high}</td>
	<td>{segment.qom}</td>
	<td>{segment.kom}</td>
	<td>{segment.athlete_count}</td>
	<td>{segment.effort_count}</td>
	<td>{segment.climb_category}</td>
	<td>{segment.city}</td>
	<td>{segment.state}</td>
	<td>{segment.country}</td>
        </tr>
    );
}

export default SegmentRow;