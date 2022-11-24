import SegmentRow from "./segmentRow";
import {useState, useEffect} from 'react';


const SegmentList = () => {
    const [segments, setSegments] = useState([]);

    const querySegments = () => {
        
        const swLat = 51.4;
        const swLong = -0.7;
        const size = 2;
        
        fetch("http://localhost:8080/api/v1/grid/discover?swLat="+swLat+"&swLong="+swLong+"&size="+size)
            .then(response => response.json()).then((data) => { setSegments(data);
            })
        
    }
    if(segments.length > 0){
    return (
        <>
            <table className="table table-hover">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>QoM Watts</th>
                    </tr>
                </thead>
                
                <tbody>
                    {segments.map(s => <SegmentRow key={s.id} segment={s}/>)}
                </tbody>

            </table>
            
        </>
    );
    } else {
        return (
            <button className="btn btn-primary" onClick={querySegments}>
                Query segments
            </button>
        );
    }
};

export default SegmentList;