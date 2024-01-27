import {Button} from "@mantine/core";

export default function Home() {

    const token = localStorage.getItem('token');

    const handleLogout = () => {
        localStorage.removeItem('token');

    }

    return (<div>
        home karşim

        {token && <Button onClick={handleLogout}>Logout</Button>}
    </div>)
}
