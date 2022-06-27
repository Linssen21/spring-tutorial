import { Layout, Menu, Breadcrumb, Table, Spin, Empty } from 'antd';
import {
    FileOutlined,
    PieChartOutlined,
    UserOutlined,
    DesktopOutlined,
    TeamOutlined,
    LoadingOutlined,
} from '@ant-design/icons';
import { useState, useEffect } from 'react';
import './App.css';

import { getAllStudents } from './client';

const { Content, Footer, Header, Sider } = Layout;

const columns = [
    {
        title: 'Id',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'Email',
        dataIndex: 'email',
        key: 'email',
    },

    {
        title: 'Gender',
        dataIndex: 'gender',
        key: 'gender',
    },
];

function getItem(label, key, icon, children) {
    return {
        key,
        icon,
        children,
        label,
    };
}

const items = [
    getItem('Option 1', '1', <PieChartOutlined />),
    getItem('Option 2', '2', <DesktopOutlined />),
    getItem('User', 'sub1', <UserOutlined />, [
        getItem('Tom', '3'),
        getItem('Bill', '4'),
        getItem('Alex', '5'),
    ]),
    getItem('Team', 'sub2', <TeamOutlined />, [
        getItem('Team 1', '6'),
        getItem('Team 2', '8'),
    ]),
    getItem('Files', '9', <FileOutlined />),
];

const loaderIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

function App() {
    const [students, setStudents] = useState([]);
    const [collapsed, setCollapsed] = useState(false);
    const [fetching, setFetching] = useState(true);

    const fetchStudents = () =>
        getAllStudents()
            .then((res) => res.json())
            .then((data) => {
                setStudents(data);
                setFetching(false);
            });

    /**
     * Empty Array on second args mean zero dependencies
     */
    useEffect(() => {
        console.log('Component is mounted');
        fetchStudents();
    }, []);

    const renderStudents = () => {
        if (fetching) {
            return <Spin indicator={loaderIcon} />;
        }

        if (students <= 0) {
            return <Empty />;
        }

        return (
            <Table
                rowKey={(student) => student.id}
                dataSource={students}
                columns={columns}
                bordered
                title={() => 'Students'}
            />
        );
    };

    return (
        <Layout style={{ minHeight: '100vh' }}>
            <Sider
                collapsible
                collapsed={collapsed}
                onCollapse={(value) => setCollapsed(value)}>
                <div className="logo" />
                <Menu
                    theme="dark"
                    defaultSelectedKeys={['1']}
                    mode="inline"
                    items={items}
                />
            </Sider>
            <Layout className="site-layout">
                <Header
                    className="site-layout-background"
                    style={{ padding: 0 }}
                />
                <Content style={{ margin: '0 16px' }}>
                    <Breadcrumb style={{ margin: '16px 0' }}>
                        <Breadcrumb.Item>User</Breadcrumb.Item>
                        <Breadcrumb.Item>Bill</Breadcrumb.Item>
                    </Breadcrumb>
                    <div
                        className="site-layout-background"
                        style={{ padding: 24, minHeight: 360 }}>
                        {renderStudents()}
                    </div>
                </Content>
                <Footer style={{ textAlign: 'center' }}>
                    Ant Design ©2022 Created by Linssen
                </Footer>
            </Layout>
        </Layout>
    );
}

export default App;
