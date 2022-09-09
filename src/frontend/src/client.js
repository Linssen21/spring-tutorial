import fetch from 'unfetch';
/**
 * @deprecated
 */
const checkStatus = (response) => {
    if (response.ok) {
        return response;
    }
    // convert non-2xx HTTP response into errors:
    const error = new Error(response.statusText);
    error.response = response;
    return Promise.reject(error);
};
/**
 * @deprecated
 */
export const getAllStudents = () => fetch('api/v1/students').then(checkStatus);
/**
 * @deprecated
 */
export const addNewStudent = (student) =>
    fetch('api/v1/students', {
        headers: {
            'Content-Type': 'application/json',
        },
        method: 'POST',
        body: JSON.stringify(student),
    });
/**
 * @deprecated
 */
export const deleteStudent = (id) =>
    fetch(`api/v1/students/${id}`, {
        headers: {
            'Content-Type': 'application/json',
        },
        method: 'DELETE',
    }).then(checkStatus);
