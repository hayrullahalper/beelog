import {Route, Routes as BaseRoutes} from "react-router-dom";
import {paths} from "./paths.ts";
import Home from "./Home";
import Login from "./Login";
import Layout from "../components/Layout.tsx";
import Register from "./Register";

export default function Routes() {
    return (
            <BaseRoutes>
                <Route element={<Layout/>}>
                    <Route path={paths.home} element={<Home/>}/>
                    <Route path={paths.login} element={<Login/>}/>
                    <Route path={paths.register} element={<Register/>}/>
                </Route>
            </BaseRoutes>

    );
}
