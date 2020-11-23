# HR Freelancers
HR Freelancers merupakan aplikasi bagi para pencari pekerjaan dengan design yang simpel dan alur perekrutan yang handal.


## Checklists
- [x] <strong>Request</strong> data dari API berjalan dengan baik.
- [x] Dapat melakukan <strong>Login</strong>, <strong>Register</strong>, <strong>Update Profile</strong>, <strong>Add & Update Experience</strong>, <strong>Add & Update Porfolio</strong>, <strong>Update Skill</strong>, dan lain-lain
- [x] Data list worker yang ditampilkan menggunakan <strong>RecyclerView</strong>.
- [x] Menggunakan <strong>TabLayout</strong>, <strong>BottomNavigationView</strong> atau yang lainnya sebagai navigasi antara halaman <strong>Detail</strong>, <strong>Experience</strong>, dan <strong>Portofolio</strong>.
- [x] Terdapat <strong>indikator loading</strong> saat aplikasi memuat data.

## Resource
Untuk aplikasi ini sendiri saya develop beserta back-end nya <strong>[BACK-END HR APP](https://github.com/Budi77Darmawan/Simple_BackEnd)</strong>.
Adapun beberapa endpoint api yang digunakan, yaitu :

- Register : http://18.212.194.218:8080/account/register
- Login : http://18.212.194.218:8080/account/login
- List Worker : http://18.212.194.218:8080/freelancers
- Detail Worker : http://18.212.194.218:8080/freelancers/<strong>{id_worker}</strong>
- List Worker : http://18.212.194.218:8080/freelancers
- Detail Worker : http://18.212.194.218:8080/freelancers/<strong>{id_worker}</strong>
- Experience Worker : http://18.212.194.218:8080/experience/<strong>{id_worker}</strong>
- Portfoliio Worker : http://18.212.194.218:8080/portofolio/<strong>{id_worker}</strong>
- Dan beberap endpoint lainnya.

## Feature :
- Menggunakan MVVM (Model-View-ViewModel) Architecture
- Menggunakan ViewBinding & DataBinding
- Menggunakan Retrofit to request api network
- Menggunakan Kotlin Coroutine untuke melakukan proses async
- Menggunakan Glide for load and caching image dari URL
- Dan lain-lain

## Link Apk
<strong>[HR Freelancers](https://bit.ly/hiringapps-freelancers)</strong>

## Demo Apk
<h3 align="center"> Login & Register Screen </h3>
<p align="center">
    <img src="https://user-images.githubusercontent.com/46107627/99956409-f017e280-2dc0-11eb-9a8e-07099bb57509.png"  
        style="margin-right: 10px;"    
        width="200" />
    <img src="https://user-images.githubusercontent.com/46107627/99956417-f3ab6980-2dc0-11eb-9101-afd7e562e5de.png"
        style="margin-right: 10px;"    
        width="200" />
</p>

<h3 align="center"> Home Screen </h3>
<p align="center">
    <img src="https://user-images.githubusercontent.com/46107627/99956491-1178ce80-2dc1-11eb-9b11-8bb767bde21d.png"  
        style="margin-right: 10px;"    
        width="200" />
    <img src="https://user-images.githubusercontent.com/46107627/99956511-19d10980-2dc1-11eb-99bd-a8048a68f7cc.gif"
        style="margin-right: 10px;"    
        width="200" />
</p>

<h3 align="center"> Detail Worker Screen </h3>
<p align="center">
    <img src="https://user-images.githubusercontent.com/46107627/99956623-48e77b00-2dc1-11eb-8071-a3032ff136c1.png"  
        style="margin-right: 10px;"    
        width="200" />
    <img src="https://user-images.githubusercontent.com/46107627/99957073-11c59980-2dc2-11eb-8611-99f8414a5d25.gif"
        style="margin-right: 10px;"    
        width="200" />
</p>

<h3 align="center"> Search Screen </h3>
<p align="center">
    <img src="https://user-images.githubusercontent.com/46107627/99957248-55b89e80-2dc2-11eb-8fcf-d07f998b41e6.png"  
        style="margin-right: 10px;"    
        width="200" />
    <img src="https://user-images.githubusercontent.com/46107627/99957251-581af880-2dc2-11eb-991b-4e9a11f439bb.png"
        style="margin-right: 10px;"    
        width="200" />
    <img src="https://user-images.githubusercontent.com/46107627/99957258-5bae7f80-2dc2-11eb-8d15-eb9c107c3997.gif"
        style="margin-right: 10px;"    
        width="200" />
</p>

<h3 align="center"> Offering Screen </h3>
<p align="center">
    <img src="https://user-images.githubusercontent.com/46107627/99957371-8ef10e80-2dc2-11eb-9819-17d9951b7fea.png"  
        style="margin-right: 10px;"    
        width="200" />
    <img src="https://user-images.githubusercontent.com/46107627/99957391-96181c80-2dc2-11eb-902f-8bb6fca7709a.png"
        style="margin-right: 10px;"    
        width="200" />
</p>

<h3 align="center"> Profile & Edit Profile Screen </h3>
<p align="center">
    <img src="https://user-images.githubusercontent.com/46107627/99957486-bea01680-2dc2-11eb-82b3-bff344fddce9.png"  
        style="margin-right: 10px;"    
        width="200" />
    <img src="https://user-images.githubusercontent.com/46107627/99957499-c495f780-2dc2-11eb-844b-01c829775a6b.png"
        style="margin-right: 10px;"    
        width="200" />
    <img src="https://user-images.githubusercontent.com/46107627/99957509-c6f85180-2dc2-11eb-83b0-736ba053f017.png"  
        style="margin-right: 10px;"    
        width="200" />
</p>

### Author
<strong>[Budi Darmawan](https://github.com/Budi77Darmawan)</strong>
