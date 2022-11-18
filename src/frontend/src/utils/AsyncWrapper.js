export async function asyncWrapper(promise) {
    try {
        return [null, await promise];
    } catch (err) {
        return [err];
    }
}
