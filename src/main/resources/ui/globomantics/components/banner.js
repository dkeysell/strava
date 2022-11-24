import { useContext } from "react";
import { logo, subtitleStyle } from "./banner.module.css"
import { navigationContext } from "./app";
import navValues from "../helpers/navValues";

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
            <div className="col-7 mt-5" style={subtitleStyle}>
                {children}
            </div>
        </header>
    )
}

export default Banner;