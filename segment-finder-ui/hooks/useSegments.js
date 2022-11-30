import { useState } from 'react';
import loadingStatus from '../helpers/loadingStatus';

const UseSegments = ()  => {
    const [segments, setSegments] = useState([]);
    const [loadingState, setLoadingState] = useState(loadingStatus.loaded);
    
    setLoadingState(loadingStatus.isLoading);
        const swLat = 53.4;
        const swLong = -0.12;
        const size = 5;
        try {
        fetch("http://localhost:8080/api/v1/grid/discover?swLat="+swLat+"&swLong="+swLong+"&size="+size)
            .then(response => response.json()).then((data) => { setSegments(data);
            });
            setLoadingState(loadingStatus.loaded);
        } catch {
            setLoadingState(loadingStatus.hasErrored);
        }
    return {segments, loadingState};
};

export default UseSegments;