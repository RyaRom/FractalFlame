import {useContext} from "react";
import {AppContext} from "../App";

export const ImageSettings = () => {
    const {setSettings} = useContext(AppContext);

    const updateSettings = (field, type) => (e) => {
        const value = type === 'number' ? parseInt(e.target.value) : parseFloat(e.target.value);
        setSettings(prevSettings => ({
            ...prevSettings,
            [field]: value
        }));
    };

    return (
        <div style={{marginBottom: 10}}>
            <input
                className="input-small"
                type="number"
                step="100"
                placeholder={"height"}
                onChange={updateSettings('height', 'number')}
            />
            <input
                className="input-small"
                type="number"
                step="100"
                placeholder={"width"}
                onChange={updateSettings('width', 'number')}
            />
            <input
                className="input-small"
                type="number"
                step="0.1"
                placeholder={"depth (1.77)"}
                onChange={updateSettings('depth', 'number')}
            />
            <input
                className="input-small"
                type="number"
                step="0.1"
                placeholder={"gamma (2.2)"}
                onChange={updateSettings('gamma', 'number')}
            />

            <button style={{marginRight: 10}}>
                start generation
            </button>

            <button>
                render
            </button>
        </div>
    )
}
