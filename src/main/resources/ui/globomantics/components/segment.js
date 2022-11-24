import { useContext } from "react";
import { navigationContext } from "./app";

const Segment = () => {
    const {param: segment } = useContext(navigationContext);
    return (
        <div className="row">
            <div className="col-4">
                <div className="row">
                    <img className="img-fluid" 
                         src={segment.elevation_profile}
                         alt="Segment profile"
                         />
                </div>
            </div>
            <div className="col-6">
                <div className="row mt-2">
                    <h5 className="col-12">{segment.name}</h5>
                    <p>KoM Power: {segment.komWatts}</p>
                    <p>QoM Power: {segment.qomWatts}</p>
                    <p>Distance: {segment.distance}m</p>
                    <p>Average Grade: {segment.average_grade}%</p>
                </div>
            </div>
        </div>
    )
}
export default Segment;