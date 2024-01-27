import {Link, Outlet} from "react-router-dom";
import {Button, Divider, Flex, Title} from "@mantine/core";
import {paths} from "../routes/paths.ts";

export default function Layout() {
    const token = localStorage.getItem('token');

    return (
            <div>
                <Flex p="lg" gap="lg" direction="column" justify="center" align="center">
                    <Title order={1}>Beelog</Title>

                    <Button.Group>
                        <Link to={paths.home}>
                            <Button variant="default">Home</Button>
                        </Link>
                        {!token && <Link to={paths.login}>
                            <Button variant="default">Login</Button>
                        </Link>}
                        {!token && <Link to={paths.register}>
                            <Button variant="default">Register</Button>
                        </Link>}
                    </Button.Group>
                </Flex>

                <Divider/>

                <Flex justify="center" align="center">
                    <Outlet/>
                </Flex>
            </div>
    );
}
