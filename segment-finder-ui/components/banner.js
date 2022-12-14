import { useContext } from "react";
import { logo } from "./banner.module.css"
import { navigationContext } from "./app";
import navValues from "../helpers/navValues";

const subtitleStyle = {
    fontStyle: "italic",
    fontSize: "XXX-large",
    color: "coral",
};

const Banner = ({children}) => {
    const { navigate } = useContext(navigationContext);
    return (
        <header className="row mb-4">
            <div className="col-5">
                <img src="./pngegg-2.png" 
                     alt="logo" 
                     className={logo} 
                     onClick={() => navigate(navValues.home)}/>
            </div>
            <div className="col-7 mt-5v" style={subtitleStyle}>
                {children}
            </div>
        </header>
    )
}

export default Banner;