import { Row, Col, Button, Popconfirm } from 'antd';
import { useState } from 'react';
import { useStudentContext } from '../store/provider/StudentDataProvider';

const Actions = ({ name, id }) => {
    const [showConfirm, setShowConfirm] = useState(false);
    const { deleteStudent } = useStudentContext();

    const showPopConfirm = () => {
        setShowConfirm(true);
    };

    const cancelPopConfirm = () => {
        setShowConfirm(false);
    };

    const onDelete = async (id) => {
        try {
            await deleteStudent(id);
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <>
            <Row align="middle" justify="space-between" gutter={6}>
                <Col span={12}>
                    <Popconfirm
                        title={`Are you sure you want to Delete ${name}?`}
                        okText="Yes"
                        cancelText="No"
                        onConfirm={() => onDelete(id)}
                        visible={showConfirm}
                        onCancel={cancelPopConfirm}>
                        <Button danger block onClick={showPopConfirm}>
                            Delete
                        </Button>
                    </Popconfirm>
                </Col>
                <Col span={12}>
                    <Button block>Edit</Button>
                </Col>
            </Row>
        </>
    );
};

export default Actions;
