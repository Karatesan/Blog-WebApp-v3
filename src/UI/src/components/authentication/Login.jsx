import { Avatar, Box, Checkbox, CssBaseline, FormControlLabel, Grid, Link, Paper, TextField, Typography } from '@mui/material';
import Button from '@mui/material/Button';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import * as Yup from 'yup'
import { useFormik } from 'formik';
import { authenticate } from '../../services/authentication/authenticationService';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';




const Login = () => {

    const [authenticating, setAuthenticating] = useState(false)
    const navigate = useNavigate();

    const formik = useFormik({
        initialValues:{
            email: '',
            password: '',
            remember: false
        },
        validationSchema:Yup.object({
            email: Yup.string().email('Invalid email address').required('Email is required'),
            password: Yup.string().required('Password is required')
          }),
        onSubmit: (values) => handleSubmit(values),
        validateOnChange:false,
        validateOnBlur:false,
    })

    const handleSubmit = async (values) =>{
        setAuthenticating(true)
        try{
        const authentication = {email:values.email, password:values.password}
        let response = await authenticate(authentication)
        navigate('/home');
        }catch(error){
            console.error(error)
            setAuthenticating(false)
        }
        
    }

    function Copyright(props) {
        return (
          <Typography variant="body2" color="text.secondary" align="center" {...props}>
            {'Copyright © '}
            <Link color="inherit" href="https://mui.com/">
              Maciej's Gomulec Website
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
          </Typography>
        );
      }

  return (
    <Grid container component="main" sx={{height:'100vh'}}>
        <CssBaseline/>
        <Grid 
        item 
        xs={false} 
        sm={4} 
        md={7} 
        sx={{ 
            backgroundImage:'url("/src/assets/Woodworking-background.jpg")', 
            backgroundColor: (t) => t.palette.mode ==='light' ? t.palette.grey[50] : t.palette.grey[900],
            backgroundSize:'cover',
            backgroundPosition:'center',
            }}>
        </Grid>
        <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
            <Box 
                sx={{
                    my:8,
                    mx:4,
                    display:'flex',
                    flexDirection:'column',
                    alignItems:'center',
                }}
            >
                <Avatar sx={{ m:1,bgcolor:'secondary.main'}}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant='h5'>
                    Sign in
                </Typography>
                
                <Box component="form" noValidate onSubmit={formik.handleSubmit} sx={{mt:1, maxWidth:'70%'}}>
                    <TextField
                        margin='normal'
                        required
                        fullWidth
                        id='email'
                        label="Email Adress"
                        name='email'
                        autoComplete='email'
                        autoFocus
                        value={formik.values.email}
                        onChange={formik.handleChange}
                        error={formik.touched.email && formik.errors.email}
                        helperText ={formik.touched.email && formik.errors.email}
                    />
                    <TextField
                        type='password'
                        margin='normal'
                        required
                        fullWidth
                        id='password'
                        label="Password"
                        name='password'
                        autoComplete='password'
                        value={formik.values.password}
                        onChange={formik.handleChange}
                        error={formik.errors.password}
                        helperText ={formik.touched.password && formik.errors.password}
                        />
                    <FormControlLabel 
                        control={<Checkbox value="remember" color='primary' />}
                        label="Remember me"
                    />
                    <Button
                        type='submit'
                        fullWidth
                        variant='contained'
                        sx={{mt:3, mb:2}}
                        disabled = {authenticating}
                    >
                        Sign In
                    </Button>
                    <Grid container>
                        <Grid item xs>
                            <Link href="#" variant='body2'>
                            Forgot password?
                            </Link>
                        </Grid>
                        <Grid item>
                            <Link href="#" variant="body2">
                                {"Don't have an account? Sign Up"}
                            </Link>
                        </Grid>
                    </Grid>
                    <Copyright sx={{ mt:5}} />
                </Box>
            </Box>
        </Grid>
    </Grid>
  )
}

export default Login