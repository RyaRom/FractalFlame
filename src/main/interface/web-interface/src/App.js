import React, {useState} from 'react';
import './App.css';
import {RenderPanel} from "./components/RenderPanel";
import {FunctionsPanel} from "./components/FunctionsPanel";
import {ImageSettings} from "./components/ImageSettings";
import {FractalImage} from "./components/FractalImage";


export const AppContext = React.createContext({});

function App() {
    const [functions, setFunctions] = useState([]);
    const [settings, setSettings] = useState([]);

    return (
        <AppContext.Provider value={{functions, setFunctions, settings, setSettings}}>
            <div className="app">
                <div className="render-panel">
                    <RenderPanel/>
                </div>
                <div className="func-panel">
                    <FunctionsPanel/>
                </div>
                <div className="image-settings">
                    <ImageSettings/>
                </div>
                <div className="fractal">
                    <FractalImage/>
                </div>
            </div>
        </AppContext.Provider>

    );
}

export default App;
