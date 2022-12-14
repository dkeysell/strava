import navValues from "../helpers/navValues";
import Segment from "./segment";
import SegmentList from "./segmentList";

const ComponentPicker = ({currentNavLocation, segments, setSegments, coordinates, setCoordinates}) => {
    switch (currentNavLocation) {
        case navValues.home:
            return <SegmentList segments={segments} setSegments={setSegments} coordinates={coordinates} setCoordinates={setCoordinates}/>;
        case navValues.segment:
            return <Segment/>;
        default:
            return (
                <h3>
                    No component for navigation value {currentNavLocation} found
                </h3>
            );
    }
};

export default ComponentPicker;