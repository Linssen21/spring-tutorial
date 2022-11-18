import { StudentDataProvider } from './provider/StudentDataProvider';

export const AppProviders = ({ children }) => {
    return <StudentDataProvider>{children}</StudentDataProvider>;
};
