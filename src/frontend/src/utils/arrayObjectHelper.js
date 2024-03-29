export function removeObjectFromArray(array, key, value) {
    const index = array.findIndex((obj) => obj[key] === value);
    return index >= 0
        ? [...array.slice(0, index), ...array.slice(index + 1)]
        : array;
}
