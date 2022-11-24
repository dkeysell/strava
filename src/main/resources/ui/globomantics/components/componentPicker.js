import navValues from "../helpers/navValues";
import Segment from "./segment";
import SegmentList from "./segmentList";

const ComponentPicker = ({currentNavLocation}) => {
    switch (currentNavLocation) {
        case navValues.home:
            return <SegmentList/>;
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