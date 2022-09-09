import Student from './components/Student';
import { AppProviders } from './store/AppProviders';

const App = () => {
    return (
        <AppProviders>
            <Student />
        </AppProviders>
    );
};

export default App;
