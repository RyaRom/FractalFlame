import {AppContext, server} from "../App";
import {useContext, useEffect, useState} from "react";

export const ContextValidator = () => {
    const {setIsCreating, setIsRendering} = useContext(AppContext);
    const {functions, settings, isCreating, isRendering} = useContext(AppContext);
    const {fractalId, setFractalId} = useContext(AppContext);
    const {setFractalImage} = useContext(AppContext);
    const {setProfilingData} = useContext(AppContext);
    const [isStarted, setIsStarted] = useState(false);

    const fetchProgress = async () => {
        try {
            const response = await fetch(`${server}/api/progress/${fractalId}`,
                {
                    method: "GET",
                    credentials: "include"
                })
            if (response.ok) {
                const img = await response.text();
                setFractalImage(img);
            } else {
                console.log(await response.text());
            }
        } catch (e) {
            console.log(e)
        }
    }

    useEffect(() => {
        if (!isStarted) return
        const intervalId = setInterval(() => {
            console.log("Generating...")
            fetchProgress()
        }, 4000);
        return () => clearInterval(intervalId);
    }, [fractalId, isStarted, fetchProgress]);

    const terminate = async () => {
        console.log("Terminating...")
        try {
            await fetch(`${server}/api/stop/${fractalId}`, {
                method: "DELETE",
                credentials: "include"
            })
        } catch (e) {
            console.log(e)
        }
    }

    const startGeneration = async () => {

        const fractal = {
            functions: [...functions],
            ...settings,
        };

        if (isCreating) {
            //terminate if was creating
            terminate()
        }
        setIsCreating(true)


        console.log(JSON.stringify(fractal, null, 2))
        try {
            const response = await fetch(`${server}/api/generate`, {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(fractal),
            });
            if (!response.ok) {
                alert("Error: " + response.status);
                console.log(await response.text());
            }

            const id = await response.text();
            console.log(id);
            setFractalId(id);

            setIsStarted(true);
        } catch (e) {
            alert(e)
            console.log(e)
        }
    }



    const onRender = async () => {
        try {
            const response = await fetch(`${server}/api/render/${fractalId}`, {
                method: "POST",
                credentials: "include"
            })

            const data = await response.json();
            console.log(`Received data on render: ${JSON.stringify(data)}`);
            setProfilingData(data);
            setIsRendering(false)
            setFractalImage(data["base64Image"])
        } catch (e) {
            console.log(e)
        }
    }

    return (
        <div>
            <button
                style={{marginRight: 10}}
                onClick={() => {
                    setIsRendering(false)
                    startGeneration().then(r => {
                        console.log("Generation started")
                    })
                }}
            >
                start generation
            </button>

            <button
                style={{marginRight: 10}}
                onClick={() => {
                    setIsCreating(false)
                    setIsRendering(true)
                    setIsStarted(false)
                    onRender()
                }}
            >
                render
            </button>
            <br/>
            <br/>
            <button
                onClick={() => {
                    setIsRendering(false)
                    setIsCreating(false)
                    setIsStarted(false)
                    terminate()
                }}
            >
                stop everything
            </button>
        </div>
    )
}
