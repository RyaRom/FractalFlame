import {useContext} from "react";
import {AppContext} from "../App";
import {ContextValidator} from "./ContextValidator";

export const ImageSettingsPanel = () => {
    const {setSettings} = useContext(AppContext);

    const updateSettings = (field, type) => (e) => {
        const value = type === 'number' ? parseInt(e.target.value) : parseFloat(e.target.value);
        setSettings(prevSettings => ({
            ...prevSettings,
            [field]: value
        }));
    };

    return (
        <div>
            <input
                className="input-small"
                type="number"
                step="100"
                min="1"
                placeholder={"height"}
                onChange={updateSettings('height', 'number')}
            />
            <input
                className="input-small"
                type="number"
                step="100"
                min="1"
                placeholder={"width"}
                onChange={updateSettings('width', 'number')}
            />
            <input
                className="input-small"
                type="number"
                step="0.1"
                min="1.0"
                placeholder={"depth (1.77)"}
                onChange={updateSettings('depth', 'float')}
            />
            <input
                className="input-small"
                type="number"
                step="0.1"
                min="0.01"
                placeholder={"gamma (2.2)"}
                onChange={updateSettings('gamma', 'float')}
            />

            <ContextValidator/>
        </div>
    )
}
