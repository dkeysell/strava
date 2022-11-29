import { GoogleMap, MarkerF } from "@react-google-maps/api";
import { useCallback, useMemo, useRef, useState } from "react";



const SegmentMap = ({coordinates, setCoordinates}) => {
    const [markerPos, setMarkerPos] = useState({lat: 51.4, lng: -0.12});
    
    const mapRef = useRef();
    const center = useMemo(() => ({lat: 51.4, lng: -0.12}),[]);
    
    const onClick = (ev) => {
        setMarkerPos({lat: ev.latLng.lat(), lng: ev.latLng.lng()})
        //markerPos.lat = ev.latLng.lat();
        //markerPos.lng = ev.latLng.lng();
        const lat = ev.latLng.lat();
        const lng = ev.latLng.lng();
        console.log("1: " + lat + " - " + lng);
        updateCoordinates(lat, lng);
      };

      const updateCoordinates = (lat , lng) => {
        console.log("2: " + lat + " - " + lng);
    setCoordinates({...coordinates, swLat: lat.toFixed(2), swLong: lng.toFixed(2)});
    
    };
    
    
    

    const options = useMemo(() => ({
        disableDefaultUI: true,
        clickableIcons: false
    }), []);
    const onLoad = useCallback(map => (mapRef.current = map), []);
    
    
    return (<GoogleMap 
                zoom={10} 
                center={center} 
                mapContainerClassName="map-container"
                options={options}
                onLoad={onLoad}
                onClick={onClick}
                >
        <MarkerF position={markerPos}/>
    </GoogleMap>);
};

export default SegmentMap;