import SegmentTable from "./segmentTable";
import SegmentMap from "./SegmentMap";
import {useState} from 'react';
import { useLoadScript } from "@react-google-maps/api";


const SegmentList = ({segments, setSegments, coordinates, setCoordinates}) => {
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

    const { isLoaded } = useLoadScript({googleMapsApiKey: process.env.NEXT_PUBLIC_GOOGLE_MAPS_API_KEY});
    if(!isLoaded) return <div>Loading...</div>
    return (
        
        <>
            <SegmentMap coordinates={coordinates} setCoordinates={setCoordinates}/>
            <form onSubmit={submit}>
                <input type="text" value={coordinates.swLat}
                       onChange={(e)=> setCoordinates({...coordinates, swLat: e.target.value})}/>
                <input type="text" value={coordinates.swLong}
                       onChange={(e)=> setCoordinates({...coordinates, swLong: e.target.value})}/>
                <input type="text" value={coordinates.size}
                       onChange={(e)=> setCoordinates({...coordinates, size: e.target.value})}/>
                <button className="btn btn-primary">Query segments</button>
            </form>
            
            <SegmentTable segments={segments}/>
        </>
    );
    
};

export default SegmentList;