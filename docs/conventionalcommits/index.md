# Conventional Commits

Conventional Commits is a method used in software development to write clear and consistent commit messages. These messages help teams understand changes in the codebase and collaborate more effectively.

## Format

A conventional commit message has several optional components that can be included to provide more information about the change:

1. **Type**: The type of the change being made. It categorizes the commit. Common types include `feat`, `fix`, `docs`, `chore`, and `style`. Additionally, you can use `feat!` and `chore!` to indicate a breaking change or significant update within that category. For example:
   - `feat`: A new feature or functionality added.
   - `fix`: A bug fix.
   - `docs`: Documentation changes.
   - `chore`: Routine tasks, maintenance, or refactoring.
   - `style`: Changes that do not affect the code's functionality, like formatting or visual updates.
   - `feat!`: A breaking change related to a new feature.
   - `chore!`: A breaking change related to maintenance or refactoring.

2. **Scope (Optional)**: The scope provides additional context to the commit, indicating where the change is taking place.

3. **Description**: A brief summary of the change. It should be concise and descriptive.

4. **Body (Optional)**: More details about the change. It provides context, explanations, or any other relevant information.

5. **BREAKING CHANGE (Optional)**: If the change introduces backward-incompatible changes, this section should be included to explain the breaking change and how to migrate or handle it.

6. **Footer (Optional)**: Additional information like references or related tasks.

## Why Use Conventional Commits?

There are several benefits to using Conventional Commits:

- **Consistency**: It enforces a consistent message format, making it easier to understand and track changes.
- **Automation**: Automated tools can generate changelogs and version updates based on these messages.
- **Collaboration**: Clear messages help team members work together and comprehend changes quickly.
- **Easy Tracking**: Especially useful for open-source projects or larger teams to manage changes effectively.

## Examples

Some example commit messages:

- `feat(auth): implement OAuth2 authentication`
- `fix(api): resolve null pointer issue in data retrieval`
- `docs(readme): update installation instructions`
- `chore(tests): refactor unit tests for better coverage`
- `style(ui): apply responsive design to user profile page`
- `feat!(api): complete restructure of the API (BREAKING CHANGE: Requires major update of client code)`
- `chore!(dependencies): update core library to latest version (BREAKING CHANGE: Updates dependencies, requires config adjustments)`

Example with a BREAKING CHANGE:

- `feat(api): add new endpoint for v2 (BREAKING CHANGE: Changes the API structure, requires updating client code)`

## Conclusion

By using Conventional Commits, developers can communicate changes more clearly. This leads to better teamwork, organized documentation, and improved software development processes.
