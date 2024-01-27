import {Route, Routes as BaseRoutes} from "react-router-dom";
import {paths} from "./paths.ts";
import Home from "./Home";
import Login from "./Login";

export default function Routes() {
    return (
            <BaseRoutes>
                <Route path={paths.home} element={<Home/>}/>
                <Route path={paths.login} element={<Login/>}/>
                {/*<Route path={paths.register} element={<Register/>}/>*/}

            </BaseRoutes>

    );
}
