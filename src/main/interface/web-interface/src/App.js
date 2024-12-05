import './App.css';
import {RenderPanel} from "./components/RenderPanel";
import {FunctionsPanel} from "./components/FunctionsPanel";
import {ImageSettings} from "./components/ImageSettings";
import {FractalImage} from "./components/FractalImage";

function App() {
    return (
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
    );
}

export default App;
