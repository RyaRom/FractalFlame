import {useState} from "react";

const variations = [
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
    const [functions, setFunctions] = useState([]);

    const addFunction = () => {
        setFunctions([...functions, {
            weight: 0,
            rgb: [0, 0, 0],
            affine: [0, 0, 0, 0, 0, 0],
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
                        deleteFunc={deleteFunction}
                    />
                ))}
            </div>
            <button onClick={addFunction}>Добавить функцию</button>
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
            <h3>Функция {index}</h3>
            <label>
                Вес:
                <input
                    type="number"
                    step="0.1"
                    value={funcData.weight}
                    onChange={(e) => handleChange('weight', parseFloat(e.target.value))}
                    placeholder={"0.5"}
                />
            </label>

            <br/>

            <label>
                Цвет:
                <input
                    type="text"
                    value={funcData.rgb.join(", ")}
                    onChange={(e) => {
                        const values = e.target.value.split(', ');
                        handleChange('rgb', values);  // Обновляем состояние как массив чисел
                    }}
                    placeholder={"255, 255, 255"}
                />
            </label>

            <br/>

            <label>
                Афинная функция:
                <input
                    type="text"
                    value={funcData.affine.join(", ")}
                    onChange={(e) => {
                        const values = e.target.value.split(', ')
                        handleChange('affine', values)
                    }}
                    placeholder={"a, b, c, d, e, f"}
                />
            </label>

            <h4>Вариации</h4>
            {funcData.variations.map((variation, vIndex) => (
                <div key={vIndex}>
                    <label>
                        Тип:
                        <select
                            value={variation.name}
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
                        (Вес):
                        <input
                            type="number"
                            step="0.1"
                            value={variation.weight}
                            onChange={(e) => {
                                const updatedVariations = [...funcData.variations];
                                updatedVariations[vIndex].weight = parseFloat(e.target.value);
                                handleChange('variations', updatedVariations);
                            }}
                        />
                    </label>

                    <br/>

                    <button
                        onClick={() => {
                            const updatedVars = funcData.variations
                                .filter((_, i) => i !== vIndex)
                            handleChange('variations', updatedVars)
                        }}>
                        Удалить вариацию
                    </button>

                    <br/>
                </div>
            ))}

            <button onClick={addVariation}>Добавить вариацию</button>
            <br/>
            <button onClick={() => {
                deleteFunc(index)
            }}>Удалить функцию
            </button>
        </div>
    );
};
