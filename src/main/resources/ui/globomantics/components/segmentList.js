import SegmentRow from "./segmentRow";
import {useState, useEffect} from 'react';


const SegmentList = ({segments, setSegments}) => {
    const[coordinates, setCoordinates] = useState({swLat: "lat", swLong: "long", size: "size"});
    const submit = (e) => {
       e.preventDefault();
        querySegments(coordinates)
    };
    const querySegments = (coordinates) => {
        const swLat = coordinates.swLat;
        const swLong = coordinates.swLong;
        const size = coordinates.size;
        
        fetch("http://localhost:8080/api/v1/grid/discover?swLat="+swLat+"&swLong="+swLong+"&size="+size)
            .then(response => response.json()).then((data) => { setSegments(data);
            })
        
    }
    
    return (
        <>
            <form onSubmit={submit}>
                <input type="text" value={coordinates.swLat}
                       onChange={(e)=> setCoordinates({...coordinates, swLat: e.target.value})}/>
                <input type="text" value={coordinates.swLong}
                       onChange={(e)=> setCoordinates({...coordinates, swLong: e.target.value})}/>
                <input type="text" value={coordinates.size}
                       onChange={(e)=> setCoordinates({...coordinates, size: e.target.value})}/>
                <button className="btn btn-primary">Query segments</button>
            </form>
            <table className="table table-hover">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>QoM Watts</th>
                        <th>KoM Watts</th>
                        <th>Distance</th>
                        <th>Av Grade</th>
                        <th>Max Grade</th>
                        <th>Elevation Low</th>
                        <th>Elevation High</th>
                        <th>QoM</th>
                        <th>KoM</th>
                        <th>Athlete Count</th>
                        <th>Effort Count</th>
                        <th>Climb Cat</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Country</th>
                    </tr>
                </thead>
                
                <tbody>
                    {segments.map(s => <SegmentRow key={s.id} segment={s}/>)}
                </tbody>

            </table>
            
            
            
        </>
    );
    
};

export default SegmentList;