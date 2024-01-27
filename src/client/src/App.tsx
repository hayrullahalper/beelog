import '@mantine/core/styles.css';

import {MantineProvider} from '@mantine/core';
import Routes from "./routes/Routes.tsx";
import {BrowserRouter} from "react-router-dom";

export default function App() {
    return (
            <MantineProvider>
                <BrowserRouter>
                    <Routes/>
                </BrowserRouter>

            </MantineProvider>);
}
