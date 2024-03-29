import {Alert, Button, Flex, JsonInput, PasswordInput, Stack, TextInput} from "@mantine/core";
import {FormEvent, useState} from "react";
import {Navigate} from "react-router-dom";

export default function Login() {
    const [error, setError] = useState<string>();
    const [success, setSuccess] = useState<boolean>(false);

    const token = localStorage.getItem('token');

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const formData = new FormData(e.currentTarget);
        const body = Object.fromEntries(formData.entries());

        const response = await fetch("http://localhost:8080/auth/login", {
            method: 'POST',
            body: JSON.stringify(body),
            headers: {"Content-Type": "application/json"}
        });

        if (response.status === 200) {
            const data = await response.json();
            const token = data.token;

            localStorage.setItem('token', token);

            setSuccess(true);
            setError(undefined);
            return;
        }

        const data = await response.json();
        setError(JSON.stringify(data, null, 2));
        return;

    }

    if (token) {
        return (
                <Navigate to={'/'} replace={true}/>
        )
    }

    return (<Flex direction="column" p="lg" style={{width: '400px'}}>
        <form noValidate onSubmit={handleSubmit}>
            <Stack>
                <TextInput name="username" placeholder="Username"/>
                <PasswordInput name="password" placeholder="Password"/>

                <Button type="submit">Login</Button>
            </Stack>
        </form>

        {error && <Alert color="red" mt="lg">
            <JsonInput
                    value={error}
                    formatOnBlur
                    autosize
                    readOnly
                    minRows={5}
            />
        </Alert>}
        {success && <Alert color="green" mt="lg">
            Login Başarılı
        </Alert>}
    </Flex>)
}
