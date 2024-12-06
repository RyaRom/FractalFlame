import {useContext} from "react";
import {AppContext} from "../App";

export const RenderPanel = () => {
    const {setSettings, isRendering, isCreating} = useContext(AppContext)

    const handleCheckbox = (field) => (e) => {
        // const {checked} = e.target
        setSettings(prevSettings => ({
            ...prevSettings,
            [field]: e.target.checked
        }))
    };

    const updateSettings = (field) => (e) => {
        const value = parseInt(e.target.value);
        setSettings(prevSettings => ({
            ...prevSettings,
            [field]: value
        }));
    };

    return (
        <div className="render-settings">
            <h3>Дополнительные настройки</h3>
            <label>
                <input
                    type="checkbox"
                    defaultChecked={true}
                    onChange={handleCheckbox('isGamma')}
                />
                Гамма коррекция
            </label>
            <label>
                <input
                    type="checkbox"
                    defaultChecked={true}
                    onChange={handleCheckbox('isBlur')}
                />
                Блюр
            </label>
            <label>
                <input
                    type="checkbox"
                    defaultChecked={true}
                    onChange={handleCheckbox('isConcurrent')}
                />
                Многопоточность
            </label>
            <label>
                <input
                    type="checkbox"
                    onChange={handleCheckbox('isHeatMap')}
                />
                HeatMap
            </label>
            <br/>
            <br/>
            <input
                className="input-small"
                type="number"
                step="100"
                min="1"
                placeholder={"staring points"}
                onChange={updateSettings('points')}
            />

            <input
                className="input-small"
                type="number"
                step="100"
                min="1"
                placeholder={"iterations"}
                onChange={updateSettings('iterations')}
            />

            <input
                className="input-small"
                type="number"
                step="1"
                min="1"
                placeholder={"symmetry"}
                onChange={updateSettings('symmetry')}
            />

            {isCreating ?
                <h4>
                    Генерация фрактала...
                </h4> : ''
            }
            {isRendering ?
                <h4>
                    Рендер...
                </h4> : ''
            }

        </div>
    )
}
