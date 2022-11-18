import fetch from 'unfetch';

export class StudentService {
    checkStatus(response) {
        if (response.ok) {
            return response;
        }
        // convert non-2xx HTTP response into errors:
        const error = new Error(response.statusText);
        error.response = response;
        return Promise.reject(error);
    }

    getAllStudents() {
        return fetch('api/v1/students').then(this.checkStatus);
    }

    addNewStudent(student) {
        return fetch('api/v1/students', {
            headers: {
                'Content-Type': 'application/json',
            },
            method: 'POST',
            body: JSON.stringify(student),
        }).then(this.checkStatus);
    }

    fetchDeleteStudent(id) {
        return fetch(`api/v1/students/${id}`, {
            headers: {
                'Content-Type': 'application/json',
            },
            method: 'DELETE',
        }).then(this.checkStatus);
    }
}
