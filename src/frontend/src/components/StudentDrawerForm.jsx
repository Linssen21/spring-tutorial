import { Drawer, Input, Col, Select, Form, Row, Button, Spin } from 'antd';

import { LoadingOutlined } from '@ant-design/icons';
import { useEffect, useState } from 'react';
import Notification from './Notification';
import { useStudentContext } from '../store/provider/StudentDataProvider';
const { Option } = Select;

const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

function StudentDrawerForm({ showDrawer, setShowDrawer }) {
    const onCLose = () => setShowDrawer(false);
    const [submitting, setSubmitting] = useState(false);
    const { addStudent, errorData } = useStudentContext();

    useEffect(() => {
        const isError = Object.entries(errorData).length > 0;
        if (!isError) {
            return onCLose();
        }

        Notification(
            'error',
            'Error Message',
            `${errorData.message} [${errorData.status}] [${errorData.error}]`,
        );
    }, [errorData]);

    const onFinish = async (student) => {
        setSubmitting(true);
        await addStudent(student);
        setSubmitting(false);
    };

    const onFinishFailed = (errorInfo) => {
        alert(JSON.stringify(errorInfo, null, 2));
    };

    return (
        <Drawer
            title="Create new student"
            width={720}
            onClose={onCLose}
            visible={showDrawer}
            bodyStyle={{ paddingBottom: 80 }}
            footer={
                <div
                    style={{
                        textAlign: 'right',
                    }}>
                    <Button onClick={onCLose} style={{ marginRight: 8 }}>
                        Cancel
                    </Button>
                </div>
            }>
            <Form
                layout="vertical"
                onFinishFailed={onFinishFailed}
                onFinish={onFinish}
                hideRequiredMark>
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item
                            name="name"
                            label="Name"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please enter student name',
                                },
                            ]}>
                            <Input placeholder="Please enter student name" />
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item
                            name="email"
                            label="Email"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please enter student email',
                                },
                            ]}>
                            <Input placeholder="Please enter student email" />
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item
                            name="gender"
                            label="gender"
                            rules={[
                                {
                                    required: true,
                                    message: 'Please select a gender',
                                },
                            ]}>
                            <Select placeholder="Please select a gender">
                                <Option value="MALE">MALE</Option>
                                <Option value="FEMALE">FEMALE</Option>
                                <Option value="OTHER">OTHER</Option>
                            </Select>
                        </Form.Item>
                    </Col>
                </Row>
                <Row>
                    <Col span={12}>
                        <Form.Item>
                            <Button type="primary" htmlType="submit">
                                Submit
                            </Button>
                        </Form.Item>
                    </Col>
                </Row>
                <Row>{submitting && <Spin indicator={antIcon} />}</Row>
            </Form>
        </Drawer>
    );
}

export default StudentDrawerForm;
