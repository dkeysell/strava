import Banner from "./banner";
import React, { useCallback, useState } from "react";
import navValues from "../helpers/navValues";
import ComponentPicker from "./componentPicker";

const navigationContext = React.createContext(navValues.home);

const App = () => {
    const navigate = useCallback(
        (navTo, param) => setNav({current: navTo, param, navigate}),
        []
    );
    const [segments, setSegments] = useState([]);

    const [nav, setNav] = useState({current: navValues.home, navigate});
    return (
        <navigationContext.Provider value={nav}>
            <Banner>
                <div>Segment Finder</div>
            </Banner>
            <ComponentPicker currentNavLocation={nav.current} segments={segments} setSegments={setSegments}/>
        </navigationContext.Provider>
    );
};

export { navigationContext };
export default App;