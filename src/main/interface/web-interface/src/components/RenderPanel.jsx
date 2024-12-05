import {useContext} from "react";
import {AppContext} from "../App";

export const RenderPanel = () => {
    const {setSettings} = useContext(AppContext)

    const handleCheckbox = (field) => (e) => {
        // const {checked} = e.target
        setSettings(prevSettings => ({
            ...prevSettings,
            [field]: e.target.checked
        }))
    };

    return (
        <div className="render-settings">
            <h3>Дополнительные настройки</h3>
            <label>
                <input
                    type="checkbox"
                    checked
                    onChange={handleCheckbox('isGamma')}
                />
                Гамма коррекция
            </label>
            <label>
                <input
                    type="checkbox"
                    checked
                    onChange={handleCheckbox('isBlur')}
                />
                Блюр
            </label>
            <label>
                <input
                    type="checkbox"
                    onChange={handleCheckbox('isHeatMap')}
                />
                HeatMap
            </label>
        </div>
    )
}
