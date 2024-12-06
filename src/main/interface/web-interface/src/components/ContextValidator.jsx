import {AppContext} from "../App";
import {useContext} from "react";

export const ContextValidator = () => {
    const {setIsCreating, setIsRendering} = useContext(AppContext);
    const {functions, settings, isCreating, isRendering} = useContext(AppContext);

    const startGeneration = async () => {
        const fractal = {functions: [...functions], ...settings};

        console.log(JSON.stringify(fractal, null, 2))
    }

    return (
        <div>
            <button
                style={{marginRight: 10}}
                onClick={() => {
                    setIsCreating(true)
                    setIsRendering(false)
                    startGeneration().then(r => {
                        console.log("Generation finished")
                    })
                }}
            >
                start generation
            </button>

            <button
                onClick={() => {
                    setIsCreating(false)
                    setIsRendering(true)
                }}
            >
                render
            </button>
        </div>
    )
}
