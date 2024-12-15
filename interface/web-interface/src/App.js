import React, {useState} from 'react';
import './App.css';
import {RenderPanel} from "./components/RenderPanel";
import {FunctionsPanel} from "./components/FunctionsPanel";
import {ImageSettingsPanel} from "./components/ImageSettingsPanel";
import {FractalImage} from "./components/FractalImage";

export const server = "http://127.0.0.1:8080";

export const AppContext = React.createContext({});

function App() {
    const [functions, setFunctions] = useState([]);
    const [settings, setSettings] = useState({
        isGamma: true,
        isBlur: true,
        isConcurrent: true,
        isHeatMap: false,
        points: 1000,
        iterations: 1000,
        symmetry: 1,
        height: 1500,
        width: 1500,
        depth: 1.77,
        gamma: 2.2
    });
    const [isCreating, setIsCreating] = useState(false);
    const [isRendering, setIsRendering] = useState(false);
    const [fractalImage, setFractalImage] = useState("");
    const [fractalId, setFractalId] = useState("");
    const [profilingData, setProfilingData] = useState({});

    return (
        <AppContext.Provider value={{
            functions, setFunctions,
            settings, setSettings,
            isCreating, setIsCreating,
            isRendering, setIsRendering,
            fractalImage, setFractalImage,
            fractalId, setFractalId,
            profilingData, setProfilingData
        }}>
            <div className="app">
                <div className="render-panel">
                    <RenderPanel/>
                </div>
                <div className="func-panel">
                    <FunctionsPanel/>
                </div>
                <div className="image-settings">
                    <ImageSettingsPanel/>
                </div>
                <div className="fractal">
                    <FractalImage/>
                </div>
            </div>
        </AppContext.Provider>

    );
}

export default App;
