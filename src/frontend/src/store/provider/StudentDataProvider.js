import { useReducer, createContext, useContext } from 'react';
import { StudentsReducer, STUDENTS_STATE } from '../reducers/student';
import * as ACTIONS from '../actions/student';
import { StudentService } from '../../services/StudentService';
import Notification from '../../components/Notification';

export const StudentDataContext = createContext();

export const StudentDataProvider = ({ children }) => {
    const studentService = new StudentService();

    const [state, dispatch] = useReducer(StudentsReducer, STUDENTS_STATE);

    const fetchStudents = async () => {
        try {
            const data = await studentService.getAllStudents();
            const students = await data.json();
            if (students) {
                dispatch(ACTIONS.fetchStudents(students));
            }
        } catch (error) {
            errorResponse(error);
        }
    };

    const addStudent = async (student) => {
        try {
            const newStudent = await studentService.addNewStudent(student);
            if (newStudent.ok) {
                dispatch(ACTIONS.addStudent(student));
                Notification(
                    'success',
                    'Student successfully added',
                    `${student.name} was added to the system`,
                );
            }
        } catch (error) {
            errorResponse(error);
        }
    };

    const deleteStudent = async (id) => {
        try {
            const deletedStudent = await studentService.fetchDeleteStudent(id);
            if (deletedStudent.ok) {
                dispatch(ACTIONS.deleteStudent(id));
            }
        } catch (error) {
            errorResponse(error);
        }
    };

    const errorResponse = async (error) => {
        const errorResponse = await error.response.json();
        if (errorResponse) {
            dispatch(ACTIONS.errorStudents(errorResponse));
        }
    };

    return (
        <StudentDataContext.Provider
            value={{
                studentsData: state.students,
                errorData: state.error,
                fetchStudents,
                addStudent,
                deleteStudent,
            }}>
            {children}
        </StudentDataContext.Provider>
    );
};

// context consumer hook
export const useStudentContext = () => {
    // get the context
    const context = useContext(StudentDataContext);

    // if `undefined`, throw an error
    if (context === undefined) {
        throw new Error('useStudentContext was used outside of its Provider');
    }

    return context;
};
