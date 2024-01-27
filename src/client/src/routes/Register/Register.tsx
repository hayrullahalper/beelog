import {Alert, Button, Flex, JsonInput, PasswordInput, Stack, TextInput} from "@mantine/core";
import {FormEvent, useState} from "react";
import {Navigate} from "react-router-dom";

export default function Register() {
    const [error, setError] = useState<string>();
    const [success, setSuccess] = useState<boolean>(false);

    const token = localStorage.getItem('token');

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const formData = new FormData(e.currentTarget);
        const body = Object.fromEntries(formData.entries());

        const response = await fetch("http://localhost:8080/auth/register", {
            method: 'POST',
            body: JSON.stringify(body),
            headers: {"Content-Type": "application/json"}
        });

        if (response.status === 200) {
            setSuccess(true);
            setError(undefined);
            return;
        }

        if (response.status === 400) {
            const data = await response.json();
            setError(JSON.stringify(data, null, 2));
            return;
        }
    }

    if (token) {
        return (
                <Navigate to={'/'} replace={true}/>
        )
    }

    return (<Flex direction="column" p="lg" style={{width: '400px'}}>
        <form noValidate onSubmit={handleSubmit}>
            <Stack>
                <TextInput name="name" placeholder="Name"/>
                <TextInput name="username" placeholder="Username"/>
                <TextInput name="email" placeholder="Email"/>
                <PasswordInput name="password" placeholder="Password"/>

                <Button type="submit">Register</Button>
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
            Kayıt başarılı
        </Alert>}
    </Flex>)
}
