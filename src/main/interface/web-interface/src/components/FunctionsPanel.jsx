import {useContext} from "react";
import {AppContext} from "../App";

const variations = [
    '',
    'BUBBLE',
    'DISK',
    'HANDKERCHIEF',
    'HEART',
    'HORSESHOE',
    'HYPERBOLIC',
    'LINEAR',
    'POLAR',
    'SINUSOIDAL',
    'SPHERE',
    'SPIRAL',
    'SWIRL'
];

export const FunctionsPanel = () => {
    const {functions, setFunctions} = useContext(AppContext);

    const addNewFunction = () => {
        setFunctions([...functions, {
            weight: 0,
            rgb: [0, 0, 0],
            affine: [0.0, 0.0, 0.0, 0.0, 0.0, 0.0],
            variations: []
        }]);
    };

    const deleteFunction = (index) => {
        console.log("Function deleted " + index)
        const updatedFunctions = [...functions].filter((_, i) => i !== index);
        setFunctions(updatedFunctions)
    }

    const updateFunction = (index, updatedFunc) => {
        console.log("Function updated " + index)
        const updatedFunctions = [...functions];
        updatedFunctions[index] = updatedFunc;
        setFunctions(updatedFunctions);
    };

    const addVariation = (index) => {
        console.log("Function variation added " + index)
        const updatedFunctions = [...functions];
        updatedFunctions[index].variations.push({weight: 0});
        setFunctions(updatedFunctions);
    };

    const getRandomInRange = (min, max) => Math.random() * (max - min) + min;

    const getRandomWeightedVariations = () => {
        const numVariations = Math.floor(getRandomInRange(0, 6));
        const selectedVariations = [];

        let totalWeight = 0;
        for (let i = 0; i < numVariations; i++) {
            const randomWeight = getRandomInRange(0, 1);
            totalWeight += randomWeight;
            selectedVariations.push({
                weight: randomWeight,
                name: variations[Math.floor(getRandomInRange(1, variations.length))]
            });
        }
        selectedVariations.forEach(variation => {
            variation.weight /= totalWeight;
        });

        return selectedVariations;
    };

    const addRandomFunc = () => {
        let func = {
            weight: parseFloat(getRandomInRange(0.0, 1.0).toFixed(2)),
            rgb: [
                Math.floor(getRandomInRange(0, 256)),
                Math.floor(getRandomInRange(0, 256)),
                Math.floor(getRandomInRange(0, 256))
            ],
            affine: [
                parseFloat(getRandomInRange(-1.5, 1.5).toFixed(2)),
                parseFloat(getRandomInRange(-1.5, 1.5).toFixed(2)),
                parseFloat(getRandomInRange(-1.5, 1.5).toFixed(2)),
                parseFloat(getRandomInRange(-1.5, 1.5).toFixed(2)),
                parseFloat(getRandomInRange(-1.5, 1.5).toFixed(2)),
                parseFloat(getRandomInRange(-1.5, 1.5).toFixed(2))
            ],
            variations: getRandomWeightedVariations()
        };

        setFunctions([...functions, func]);
    };

    return (
        <div className="functions-scroll">
            <h2>Настройки трансформаций</h2>
            <div>
                {functions.map((func, index) => (
                    <FunctionFields
                        key={index}
                        index={index}
                        funcData={func}
                        updateFunction={updateFunction}
                        addVariation={() => addVariation(index)}
                        deleteFunc={() => deleteFunction(index)}
                    />
                ))}
            </div>
            <button
                style={{marginBottom: 10}}
                onClick={addNewFunction}>
                Add function
            </button>
            <button
                style={{marginBottom: 10}}
                onClick={addRandomFunc}>
                Random function
            </button>
        </div>
    );
}


const FunctionFields = ({index, funcData, updateFunction, addVariation, deleteFunc}) => {
    const handleChange = (field, value) => {
        const updatedFunc = {...funcData, [field]: value};
        updateFunction(index, updatedFunc);
    };

    const handleVariationChange = (index, field, value) => {
        const updatedVariations = [...funcData.variations];
        updatedVariations[index][field] = value;
        handleChange('variations', updatedVariations);
    };

    return (
        <div className="function-container">
            <h3>Function {index}</h3>
            <label>
                Weight:
                <input
                    type="number"
                    step="0.1"
                    min="0.01"
                    onChange={(e) => handleChange('weight', parseFloat(e.target.value))}
                    placeholder={"0.5"}
                    defaultValue={funcData.weight}
                />
            </label>

            <br/>

            <label>
                Color:
                <input
                    type="text"
                    onChange={(e) => {
                        const values = e.target.value.replaceAll(" ", "").split(',').map(Number);
                        handleChange('rgb', values);
                    }}
                    placeholder={"255, 255, 255"}
                    defaultValue={funcData.rgb.join(', ')}
                />
            </label>

            <br/>

            <label>
                Affine function:
                <input
                    type="text"
                    onChange={(e) => {
                        const values = e.target.value.replaceAll(" ", "").split(',').map(a => parseFloat(a))
                        handleChange('affine', values)
                    }}
                    placeholder={"a, b, c, d, e, f"}
                    defaultValue={funcData.affine.join(', ')}
                />
            </label>

            <h4>Variations</h4>
            {funcData.variations.map((variation, vIndex) => (
                <div key={vIndex} style={{marginBottom: '10px'}}>
                    <label className="variation-label">
                        Type:
                        <select
                            defaultValue={variation.name}
                            onChange={(e) => {
                                handleVariationChange(vIndex, 'name', e.target.value);
                            }}
                        >
                            {variations.map((variationName, index) => (
                                <option key={index} value={variationName}>
                                    {variationName}
                                </option>
                            ))}
                        </select>
                    </label>

                    <br/>

                    <label>
                        Weight:
                        <input
                            type="number"
                            step="0.1"
                            min="0.01"
                            onChange={(e) => {
                                const updatedVariations = [...funcData.variations];
                                updatedVariations[vIndex].weight = parseFloat(e.target.value);
                                handleChange('variations', updatedVariations);
                            }}
                            placeholder={"0.5"}
                            defaultValue={variation.weight}
                        />
                    </label>

                    <br/>

                    <button
                        onClick={() => {
                            const updatedVars = funcData.variations
                                .filter((_, i) => i !== vIndex)
                            handleChange('variations', updatedVars)
                        }}
                    >
                        Delete variation
                    </button>
                    <br/>
                </div>
            ))}

            <button onClick={addVariation}>Add variation</button>
            <br/>
            <button onClick={deleteFunc}>
                Delete function
            </button>
        </div>
    );
};
