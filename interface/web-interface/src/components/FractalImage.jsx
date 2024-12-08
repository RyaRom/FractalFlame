import {useContext} from "react";
import {AppContext} from "../App";

export const FractalImage = () => {
    const {fractalImage, setFractalImage} = useContext(AppContext);

    if (fractalImage === "") return <div/>
    if (!fractalImage.startsWith("data:image/png;base64,")) {
        setFractalImage("data:image/png;base64," + fractalImage);
    }

    return (
        <div>
            <img
                src={fractalImage}
                alt="Loading..."
                className="pic"
            />
        </div>
    )
}
