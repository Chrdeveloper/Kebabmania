"""
URL configuration for myproject project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path

from kebabmania import endpoint_user, endpoint_log, endpoint_opinion, endpoint_kebab, endpoint_city, endpoint_food, \
    endpoint_home
#Las Url del api rest
urlpatterns = [
    path('admin/', admin.site.urls),
    path('user/<str:tlf>', endpoint_user.editarborrar),
    path('user', endpoint_user.crear),
    path('login', endpoint_log.crearsesion),
    path('log', endpoint_log.keep_login),
    path('opinion/<str:tlf>', endpoint_opinion.opinions),
    path('kebabs/<int:cityId>', endpoint_kebab.kebabcity),
    path('cities', endpoint_city.allcity),
    path('food', endpoint_food.allfood),
    path('home', endpoint_home.home)
]
